package com.example.sockt;//package com.example.sockt;
//
//import com.example.model.entity.SocketMsg;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.json.JsonParseException;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * @Auther: morou
// * @Description: websocket的具体实现类
// * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
// * 但在springboot中连容器都是spring管理的。
//    虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
//    所以可以用一个静态set保存起来。
// */
//
//@ServerEndpoint(value = "/websocket/{nickname}")
////类似于comtroller，但是一个是单通道的，这个websocket是双通道的这个是地址类似于@RequestMapper那样子
//@Component//注册到IOC容器中
//public class MyWebSocket {
//    //用来存放每个客户端对应的MyWebSocket对象。
//    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//    private String nickname;
//    //用来记录sessionId和该session进行绑定
//    private static Map<String,Session> map = new HashMap<String, Session>();
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session,@PathParam("nickname") String nickname) {
//        this.session = session;
//        this.nickname=nickname;
//
//        map.put(session.getId(), session);
//
//        webSocketSet.add(this);     //加入set中
//        System.out.println("有新连接加入:"+nickname+",当前在线人数为" + webSocketSet.size());
//        this.session.getAsyncRemote().sendText("恭喜"+nickname+"成功连接上WebSocket(其频道号："+session.getId()+")-->当前在线人数为："+webSocketSet.size());
//    }
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this);  //从set中删除
//        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
//    }
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session,@PathParam("nickname") String nickname) {
//        System.out.println("来自客户端的消息-->"+nickname+": " + message);
//
//        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
//        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
//        ObjectMapper objectMapper = new ObjectMapper();
//        SocketMsg socketMsg;
//
//        try {
//            socketMsg = objectMapper.readValue(message, SocketMsg.class);
//            if(socketMsg.getType() == 1){
//                //单聊.需要找到发送者和接受者.
//
//                socketMsg.setFromUser(session.getId());//发送者.
//                Session fromSession = map.get(socketMsg.getFromUser());
//                Session toSession = map.get(socketMsg.getToUser());
//                //发送给接受者.
//                if(toSession != null){
//                    //发送给发送者.
//                    fromSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());
//                    toSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());
//                }else{
//                    //发送给发送者.
//                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
//                }
//            }else{
//                //群发消息
//                broadcast(nickname+": "+socketMsg.getMsg());
//            }
//
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
////        //群发消息
////        broadcast(nickname+": "+message);
//    }
//    /**
//     * 发生错误时调用
//     *
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//    /**
//     * 群发自定义消息
//     * */
//    public  void broadcast(String message){
//        for (MyWebSocket item : webSocketSet) {
//            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
//            //this.session.getBasicRemote().sendText(message);
//            item.session.getAsyncRemote().sendText(message);//异步发送消息.
//        }
//    }
//}
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

//
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
import com.example.model.entity.SocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/imserver/{username}")
@Component
public class MyWebSocket{

    private static final Logger log = LoggerFactory.getLogger(MyWebSocket.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen//客户端和服务端建立连接后进入到这里来
    public void onOpen(Session session, @PathParam("username") String username) {

        //将在线的用户put进去那个集合里面
        sessionMap.put(username, session);
        //log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());

        //在线人数的列表
        JSONObject result = new JSONObject();

        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
       // sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     */
    @OnMessage//就是客户端发消息给服务端，服务端再发给指定的用户（客户端）
//    @RequestBody SocketMsg socketMsg
    public void onMessage(String message, @PathParam("username") String username) {

        JSONObject obj = JSONUtil.parseObj(message);//将收到的string类型数据转化为json格式

        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin

        String text = obj.getStr("text"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}

        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本

        if (toSession != null) {

            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            JSONObject jsonObject = new JSONObject();

            jsonObject.set("from", username);  // from 是 zhang

//            jsonObject.set("text", socketMsg.getMsg());  // text 同上面的text
            jsonObject.set("text", text);  // text 同上面的text

            this.sendMessage(jsonObject.toString(), toSession);//将上面的json格式转化为string格式发送，tosession是指发送到指定的用户

        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
