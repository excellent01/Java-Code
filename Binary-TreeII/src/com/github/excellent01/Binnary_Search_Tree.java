package com.github.excellent01;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @auther plg
 * @date 2019/5/28 22:59
 */
public class Binnary_Search_Tree<E extends Comparable<E>> implements Binnary_Tree<E> {
    private int count = 0;
    private Node root;
    private class Node{
        private E e;
        private Node left;
        private Node right;

        public Node(E e) {
            this.e = e;
        }
    }
    //记住语义
    private Node add(Node node,E e){
        if(node == null){
            node = new Node(e);
            count++;
            return node;
        }
        if(node.e.compareTo(e) == 0){
            return node;
        }
        if(node.e.compareTo(e) < 0){
           node.right = add(node.right,e);
        }else {
            node.left =  add(node.left,e);
        }
        return node;
    }
    private void add2(Node node,E e){
        if(node.e.compareTo(e) == 0){
            return;
        }
        if(node.e.compareTo(e) > 0 && node.left == null){
            node.left = new Node(e);
             return;
        }else if(node.e.compareTo(e) < 0 && node.right == null){
            node.right = new Node(e);
            return;
        }else if(node.e.compareTo(e) < 0){
            add(node.right,e);
        }else{
            add(node.left,e);
        }
    }
    @Override
    public void add(E e) {
        count++;
       if(root == null){
           root = new Node(e);
       }else {
           add2(root, e);
       }
    }

    @Override
    public void remove(E e) {
       root = remove(root,e);
    }
    private Node remove(Node node,E e){
        if(node == null){
            return null;
        }
        if(e.compareTo(node.e) < 0){
            node.left = remove(node.left,e);
        }else if(e.compareTo(node.e) > 0){
            node.right = remove(node.right,e);
        }else{
            if(node.left == null && node.right != null){
                Node rightNode = node.right;
                count--;
                node.right = null;
                return rightNode;
            }else if(node.right == null && node.left != null){
                Node leftNode = node.left;
                count--;
                node.left = null;
                return leftNode;
            }else{    // 左右双全
                // 找到后继结点
                Node succeor = getMinNode(node.right);
                succeor.left = node.left;
                // 删掉了succeor后的node赋给succeor右边
                succeor.right = removeMin(node.right);
                node.left = node.right = null;
                return succeor;
            }
        }
        return node;
    }

    private Node getMinNode(Node node) {
        if(node == null){
            return null;
        }
        if(node.left == null){
            return node;
        }
        return getMinNode(node.left);

    }


    // 删除最小的结点
    public Node removeMin(Node node){
        if(node == null){
            return null;
        }
        if(node.left == null){
            Node rigthNode = node.right;
            node.right = null;
            count--;
            // 返回剩余的子树
            return rigthNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    // 删除最大的结点
    public Node removeMax(Node node){
        if(node == null){
            return null;
        }
        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            count--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }
    private Node getSuccessor(Node root,Node node){
        if(root == null || node == null){
            return null;
        }
        if(root.left == node || root.right == node){
            return root;
        }
        Node left = getSuccessor(root.left,node);
        Node right = getSuccessor(root.right,node);
        return left != null ? left : right;
    }
    private Node search(Node node,E e){
        if(node == null){
            return null;
        }
        if(node.e.compareTo(e) == 0){
            return node;
        }
        Node left = search(node.left,e);
        Node right = search(node.right,e);
        return left != null ? left : right;

    }

    @Override
    public boolean contains(E e) {
        //return contains(root ,e);
        return contains_(e);
    }

    /**
     * 非递归写法
     * @param e
     * @return
     */
    private boolean contains_(E e){
        Node p = root;
        while(p != null && p.e.compareTo(e) != 0){
            if(e.compareTo(p.e) == 0){
                return true;
            }else if(e.compareTo(p.e) > 0){
                p = p.right;
            }else{
                p = p.left;
            }
        }
        return !(p == null);
    }

    /**
     * 递归写法
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node, E e) {
        if(node == null) {
            return false;
        }
        if(node.e.compareTo(e) == 0){
            return true;
        }else if(node.e.compareTo(e) > 0){
            return contains(node.left,e);
        }else{
            return contains(node.right,e);
        }
    }

    @Override
    public boolean modify(E e) {
        return false;
    }

    @Override
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if(node == null){
            return;
        }
        System.out.print(node.e + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    @Override
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.e + " ");
        inOrder(node.right);
    }

    @Override
    public void postOrder() {
        if(root == null){
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while(!stack1.isEmpty()){
            Node temp = stack1.pop();
            stack2.push(temp);
            if(temp.left != null){
                stack1.push(temp.left);
            }
            if(temp.right != null){
                stack1.push(temp.right);
            }
        }
        while(!stack2.isEmpty()){
            System.out.print(stack2.pop().e + " ");
        }
        System.out.println();
    }

    @Override
    public void leverOrder() {
        if(root == null){
            return;
        }
        Node last = root;
        Node nlast = null;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
           Node temp = queue.poll();
            System.out.print(temp.e + " ");
            if(temp.left != null){
                queue.offer(temp.left);
                nlast = temp.left;
            }
            if(temp.right != null){
                queue.offer(temp.right);
                nlast = temp.right;
            }
            if(temp == last){
                System.out.println();
                last = nlast;
            }
        }
    }

    public int getSize(){
        return this.count;
    }
    public boolean delete(E e){
        if(root == null){
            return false;
        }
        Node cur = root;
        Node parent = null;
        // 搜索
        while(cur != null){
            if(cur.e.compareTo(e) == 0){
                break;
            }
            if(cur.e.compareTo(e) > 0){
                parent = cur;
                cur = cur.left;
            }else{
                parent = cur;
                cur = cur.right;
            }
        }
        // 删除
        if(cur == null){
            return false;
        }
        // 叶子结点，直接删
        if(cur.left == null && cur.right == null){
            // 如果不是根结点
            if(cur != root){
                if(parent.left == cur){
                    parent.left = null;
                }else{
                    parent.right = null;
                }
                cur.e = null;
                cur = null;
                return true;
            }else {
                root.e = null;
                root = null;
            }
        }
        // 有右孩子无左孩子
        if(cur.left == null && cur.right != null){
            if(root == cur){
                root = root.right;
            }else{
                if(parent.left == cur){
                    parent.left = cur.right;
                    cur = null;
                }else{
                    parent.right = cur.right;
                    cur = null;
                }
            }
            return true;
        }
        // 有左孩子无右孩子
        if(cur.left != null && cur.right == null){
            if(root == cur){
                root = root.left;
            }else{
                if(parent.left == cur){
                    parent.left = cur.left;
                    cur = null;
                }else{
                    parent.right = cur.left;
                    cur = null;
                }
            }
            return true;
        }
        // 左右孩子都有,选择左边最大的或者右边最小的
        if(cur.left != null && cur.right != null){

            // 1 寻找替换结点(用左边最大的)
            Node next = cur.left;
            while(next.right != null){
                parent = next;
                next = next.right;
            }
            //置换
            cur.e = next.e;
            //移交
            Node temp = next.left;
            parent.right = temp;

        }
        return false;
    }

}
