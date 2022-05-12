package com.xmg.p2p.base;

import com.xmg.p2p.base.util.MD5;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        System.out.println(MD5.encode(new BigDecimal("2850.00000000").hashCode()+" "+new BigDecimal("0.0000").hashCode()));
    }
}
