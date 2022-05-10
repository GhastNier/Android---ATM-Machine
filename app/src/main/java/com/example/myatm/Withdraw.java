package com.example.myatm;

public class Withdraw extends MainActivity{
    public static int onClickWithdraw(int toWithdraw, int current) {
        return current - toWithdraw;
    }
}
