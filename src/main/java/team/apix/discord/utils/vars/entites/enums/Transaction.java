package team.apix.discord.utils.vars.entites.enums;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public enum Transaction {
    TRANSFER("transfer"),
    REWARD("reward"),
    STORE_SELL("store_sell"),
    STORE_BUY("store_buy"),
    TRANSACTION_SUCCESS("transaction_success"),
    TRANSACTION_FAIL("transaction_fail"),
    UNDEFINED("undefined");

    final String string;

    Transaction(String string) {
        this.string = string;
    }

    public static Transaction getType(String search){
        for (Transaction transaction : values())
            if (transaction.string.equalsIgnoreCase(search)) return transaction;
        return null;
    }

    public String getString(){
        return string;
    }
}
