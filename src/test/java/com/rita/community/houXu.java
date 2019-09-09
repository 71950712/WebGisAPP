package com.rita.community;

import reactor.core.Exceptions;

import java.util.Stack;

public class houXu {

    public static void main(String[] args)throws Exception {
        btnode a1 = new btnode();
        btnode a2 = new btnode();
        btnode a3 = new btnode();
        btnode a4 = new btnode();
        btnode a5 = new btnode();
        btnode a6 = new btnode();
        btnode a7 = new btnode();
        btnode a8 = new btnode();
        btnode a9 = new btnode();

        a1.setData("1");
        a2.setData("2");
        a2.setLeftChild(a1);
        a3.setData("3");
        a4.setData("4");
        a4.setLeftChild(a2);
        a4.setRightChild(a3);
        a5.setData("5");
        a5.setLeftChild(a4);
        a6.setData("6");
        a7.setData("7");
        a8.setData("8");
        a8.setLeftChild(a6);
        a8.setRightChild(a7);
        a9.setData("9");
        a9.setLeftChild(a5);
        a9.setRightChild(a8);

        a1.PostorderTraversal(a9);


    }


}
