package com.rita.community;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class btnode {
    private String data;
    private btnode leftChild;
    private btnode rightChild;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public btnode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(btnode leftChild) {
        this.leftChild = leftChild;
    }

    public btnode getRightChild() {
        return rightChild;
    }

    public void setRightChild(btnode rightChild) {
        this.rightChild = rightChild;
    }

 /*后序遍历实现*/
    public void PostorderTraversal(btnode p){

        btnode former =null;
        Stack<btnode> st = new Stack<>();
        while(!st.empty() || p != null){
            if(p != null){
                if(p.getLeftChild() != null){
                    st.push(p);
                    p = p.getLeftChild();
                }else if(p.getRightChild() != null){
                    st.push(p);
                    p = p.getRightChild();
                }else {
                    System.out.println(p.getData());
                    former = p;
                    p = null;
                }
            }else {
                p = st.pop();
                if(p.getLeftChild() == former && p.getRightChild() != null) {
                    st.push(p);
                    p = p.getRightChild();
                }else {
                    System.out.println(p.getData());
                    former = p;
                    p = null;

                }

            }
        }
    }
}

