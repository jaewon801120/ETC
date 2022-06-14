package com.lgcns.test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class QueueMsg {

	String name;
	int size;
	int index;
	Queue<MsgObj> que;

	public QueueMsg(String queName, int queSize) {
		super();
		this.name = queName;
		this.size = queSize;
		this.index = 0;
		this.que = new LinkedList<>();
	}

	public boolean add(String message, int msgID) {
		if (size <= que.size()) {
			return false;
		}

		que.add(new MsgObj(message, index++, msgID));

		return true;
	}

	public MsgObj get() {
		return que.poll();
	}

	public void remove() {
		que.poll();
	}

	public void restore(MsgObj msg) {

		int length = que.size();
		if (length == 0) {
			que.add(msg);
			return;
		}

		MsgObj[] array = null;

		array = que.toArray(new MsgObj[length]);

		LinkedList<MsgObj> temp = new LinkedList<MsgObj>();
		Collections.addAll(temp, array);
		Iterator<MsgObj> itr = temp.iterator();
		int cur = 0;
		while (itr.hasNext()) {
			MsgObj tempMsg = itr.next();
			if (tempMsg.getIndex() > msg.getIndex()) {
				temp.add(cur, msg);
				break;
			}

			cur++;
		}

		que = new LinkedList<MsgObj>(temp);

//		int size = que.size();
//		if (size <= index) {
//			que.add(msg);
//		} else {
//			MsgObj[] array = null;
//
//			array = que.toArray(new MsgObj[que.size()]);
//
//			System.out.println(Arrays.toString(array));
//			LinkedList<MsgObj> temp = new LinkedList<MsgObj>();
//			Collections.addAll(temp, array);
//			temp.add(index, msg);
//
//			que = new LinkedList<MsgObj>(temp);
//		}
	}
	
	public String Trace() {
		Iterator<MsgObj> it = que.iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
        	MsgObj e = it.next();
            sb.append(e.getMessage());
            if (!it.hasNext())
                return sb.append(']').toString();
            
            sb.append(',').append(' ');
        }
	}
	
	@Override
	public String toString() {
		return "QueueMsg [name=" + name + ", size=" + size + ", index=" + index + ", que=" + Trace() + "]";
	}
	
}

class MsgObj {

	String message;
	int index;
	int msgID;

	public MsgObj(String message, int index, int msgID) {
		super();
		this.message = message;
		this.index = index;
		this.msgID = msgID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

}
