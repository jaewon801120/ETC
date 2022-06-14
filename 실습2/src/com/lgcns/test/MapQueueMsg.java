package com.lgcns.test;

import java.util.HashMap;

public class MapQueueMsg {

	public static HashMap<String, QueueMsg> mapQueue = new HashMap<>();
	public static int msgID = 0;
	public static HashMap<String, MsgObj> restoreMsg = new HashMap<>();

	public static boolean CreateQue(String name, int size) {
		QueueMsg queMsg = mapQueue.get(name);
		if (queMsg != null) {
			System.out.println("Queue Exist");
			return false;
		}

		queMsg = new QueueMsg(name, size);

		mapQueue.put(name, queMsg);

		return true;
	}

	public static boolean SendQue(String name, String message) {
		QueueMsg queMsg = mapQueue.get(name);
		if (queMsg == null)
			return false;

		if (!queMsg.add(message, msgID++)) {
			System.out.println("Queue Full");
			return false;
		}

		return true;
	}

	public static MsgObj ReceiveQue(String name) {
		QueueMsg queMsg = mapQueue.get(name);
		if (queMsg == null)
			return null;

		MsgObj msg = queMsg.get();
		if (msg != null) {
			restoreMsg.put(String.valueOf(msg.getMsgID()), msg);
		}

		return msg;
	}

	public static void RemoveQue(String msgID) {
		restoreMsg.remove(msgID);
	}
	
	public static void RestoreQue(String name, String msgID) {
		MsgObj msg = restoreMsg.get(msgID);
		QueueMsg queMsg = mapQueue.get(name);
		queMsg.restore(msg);
		restoreMsg.remove(msgID);
	}

	public static void Trace(String name) {
		System.out.println(mapQueue.get(name));
	}
}
