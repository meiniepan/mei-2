/*
 * Copyright (c) 2017-2018 PLACTAL.
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.wuyou.merchant.data;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.crypto.ec.EosPrivateKey;
import com.wuyou.merchant.crypto.ec.EosPublicKey;
import com.wuyou.merchant.data.abi.EosAbiMain;
import com.wuyou.merchant.data.api.AccountInfoRequest;
import com.wuyou.merchant.data.api.EosAccountInfo;
import com.wuyou.merchant.data.api.EosChainInfo;
import com.wuyou.merchant.data.api.EosCreateVoteBean;
import com.wuyou.merchant.data.api.EosVoteBean;
import com.wuyou.merchant.data.api.GetBalanceRequest;
import com.wuyou.merchant.data.api.GetCodeRequest;
import com.wuyou.merchant.data.api.GetCodeResponse;
import com.wuyou.merchant.data.api.GetRequestForCurrency;
import com.wuyou.merchant.data.api.GetRequiredKeys;
import com.wuyou.merchant.data.api.GetTableRequest;
import com.wuyou.merchant.data.api.JsonToBinRequest;
import com.wuyou.merchant.data.api.VoteOption;
import com.wuyou.merchant.data.api.VoteQuestion;
import com.wuyou.merchant.data.chain.Action;
import com.wuyou.merchant.data.chain.PackedTransaction;
import com.wuyou.merchant.data.chain.SignedTransaction;
import com.wuyou.merchant.data.local.db.EosAccount;
import com.wuyou.merchant.data.types.EosActivityRewards;
import com.wuyou.merchant.data.types.EosDailyRewards;
import com.wuyou.merchant.data.types.EosNewAccount;
import com.wuyou.merchant.data.types.EosTransfer;
import com.wuyou.merchant.data.types.TypeAsset;
import com.wuyou.merchant.data.types.TypeChainId;
import com.wuyou.merchant.network.ChainRetrofit;
import com.wuyou.merchant.network.apis.NodeosApi;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.EosUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by swapnibble on 2017-11-03.
 */
public class EoscDataManager {
    private static EoscDataManager eoscDataManager;

    public synchronized static EoscDataManager getIns() {
        if (eoscDataManager == null) {
            eoscDataManager = new EoscDataManager();
        }
        return eoscDataManager;
    }

    public Observable<JsonObject> createAccount(String phone, String account, String publicKey) {
        EosNewAccount eosAccount = new EosNewAccount(phone, account, publicKey, new TypeAsset(2));
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).jsonToBin(new JsonToBinRequest(Constant.EOSIO_SYSTEM_ACCOUNT, eosAccount.getActionName(), CommonUtil.prettyPrintJson(eosAccount)))
                .flatMap(jsonToBinResp -> getChainInfo()
                        .map(info -> createTransaction(Constant.EOSIO_SYSTEM_ACCOUNT, eosAccount.getActionName(), jsonToBinResp.getBinargs(), getActivePermission("justforapply"), info)))
                .flatMap(signedTransaction -> {
                    final SignedTransaction stxn = new SignedTransaction(signedTransaction);
                    stxn.sign(new EosPrivateKey(chainPrivateKey), new TypeChainId(currentBlockInfo.getChain_id()));
                    return ChainRetrofit.getInstance().createApi(NodeosApi.class).pushTransactionRetJson(new PackedTransaction(stxn));
                });
    }

    private EosAccount currentOperateAccount;


    public Observable<JsonObject> createVote(String title, String logo, String description, String organization, List<VoteQuestion> contents) {
        currentOperateAccount = CarefreeDaoSession.getInstance().getMainAccount();
        long time = 480 * 3600 * 1000;
        EosCreateVoteBean activityRewards = new EosCreateVoteBean(currentOperateAccount.getName(), title, logo, description, organization, contents, EosUtil.formatTimePoint(System.currentTimeMillis() + time));
        return pushActionRetJson(Constant.ACTIVITY_CREATE_VOTE, activityRewards.getActionName(), CommonUtil.prettyPrintJson(activityRewards), getActivePermission(currentOperateAccount.getName()));
    }

    public Observable<JsonObject> doVote(String id , List<VoteOption> option){
        currentOperateAccount = CarefreeDaoSession.getInstance().getMainAccount();
        EosVoteBean voteBean = new EosVoteBean(id,currentOperateAccount.getName(),option);
        return pushActionRetJson(Constant.ACTIVITY_CREATE_VOTE, voteBean.getActionName(), CommonUtil.prettyPrintJson(voteBean), getActivePermission(currentOperateAccount.getName()));

    }

    public Observable<EosChainInfo> getChainInfo() {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).readInfo("get_info");
    }

    public Observable<String> getTable(String accountName, String code, String table, String tableKey, String lowerBound, String upperBound, int limit) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getTable(
                new GetTableRequest(accountName, code, table, tableKey, lowerBound, upperBound, limit))
                .map(tableResult -> CommonUtil.prettyPrintJson(tableResult));
    }

    public Observable<String> getTable(String accountName, String code, String table) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getTable(
                new GetTableRequest(accountName, code, table))
                .map(tableResult -> CommonUtil.prettyPrintJson(tableResult));
    }


    private SignedTransaction createTransaction(String contract, String actionName, String dataAsHex, String[] permissions, EosChainInfo chainInfo) {
        currentBlockInfo = chainInfo;
        Action action = new Action(contract, actionName);
        action.setAuthorization(permissions);
        action.setData(dataAsHex);

        SignedTransaction txn = new SignedTransaction();
        txn.addAction(action);
        txn.putSignatures(new ArrayList<>());
        if (null != chainInfo) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(Constant.TX_EXPIRATION_IN_MILSEC));
        }
        return txn;
    }

    private Observable<PackedTransaction> signAndPackTransaction(SignedTransaction txnBeforeSign) {
        ArrayList<String> pkList = new ArrayList<>();
        pkList.add(currentOperateAccount.getPublicKey());
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getRequiredKeys(new GetRequiredKeys(txnBeforeSign, pkList))
                .map(keys -> {
                    final SignedTransaction stxn;
                    stxn = signTransaction(txnBeforeSign, keys.getKeys(), new TypeChainId(currentBlockInfo.getChain_id()));
                    return new PackedTransaction(stxn);
                });
    }

    private SignedTransaction signTransaction(final SignedTransaction txn, final List<EosPublicKey> keys, final TypeChainId id) throws IllegalStateException {
        SignedTransaction stxn = new SignedTransaction(txn);
        boolean found = false;
        for (EosPublicKey pubKey : keys) {
            if (TextUtils.equals(pubKey.toString(), currentOperateAccount.getPublicKey())) {
                stxn.sign(new EosPrivateKey(currentOperateAccount.getPrivateKey()), id);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalStateException("Public key not found in unlocked wallets " + currentOperateAccount.getPublicKey());
        }
        return stxn;
    }

    private String[] getActivePermission(String accountName) {
        return new String[]{accountName + "@active"};
    }

    public Observable<EosAccountInfo> readAccountInfo(String accountName) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getAccountInfo(new AccountInfoRequest(accountName));
    }

    public Observable<JsonObject> getTransactions(String accountName) {
        JsonObject gsonObject = new JsonObject();
        gsonObject.addProperty(NodeosApi.GET_TRANSACTIONS_KEY, accountName);
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getAccountHistory(NodeosApi.ACCOUNT_HISTORY_GET_TRANSACTIONS, gsonObject);
    }

    public Observable<JsonObject> getServants(String accountName) {
        JsonObject gsonObject = new JsonObject();
        gsonObject.addProperty(NodeosApi.GET_SERVANTS_KEY, accountName);
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getAccountHistory(NodeosApi.ACCOUNT_HISTORY_GET_SERVANTS, gsonObject);
    }

    private EosChainInfo currentBlockInfo;

    private final String chainPublicKey = "EOS5kkMsEbNCR2VA2cvr4szm9RJxRD996bAGs5LbHQYp1uoo2mAzx";
    private final String chainPrivateKey = "5JcN6R1fTwSUvL3jHpnpG6KsJB3nEPBSkL63dbht4ySPrXTgXyL";

    private Observable<JsonObject> pushActionRetJson(String contract, String action, String data, String[] permissions) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).jsonToBin(new JsonToBinRequest(contract, action, data))
                .flatMap(jsonToBinResp -> getChainInfo()
                        .map(info -> createTransaction(contract, action, jsonToBinResp.getBinargs(), permissions, info)))
                .flatMap(this::signAndPackTransaction)
                .flatMap(ChainRetrofit.getInstance().createApi(NodeosApi.class)::pushTransactionRetJson);
    }

    public Observable<EosAbiMain> getCodeAbi(String contract) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getCode(new GetCodeRequest(contract))
                .filter(GetCodeResponse::isValidCode)
                .map(result -> new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                        .create().fromJson(result.getAbi(), EosAbiMain.class));
    }

    public Observable<EosAbiMain> getAbiMainFromJson(String jsonStr) {
        return Observable.just(new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create().fromJson(jsonStr, EosAbiMain.class));
    }

    public Observable<JsonArray> getCurrencyBalance(String contract, String account, String symbol) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getCurrencyBalance(new GetBalanceRequest(contract, account, symbol));

    }

    public Observable<String> getCurrencyStats(String contract, String symbol) {
        return ChainRetrofit.getInstance().createApi(NodeosApi.class).getCurrencyStats(new GetRequestForCurrency(contract, symbol))
                .map(result -> CommonUtil.prettyPrintJson(result));
    }




}
