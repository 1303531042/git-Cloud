package com.ruoyi.gateway.client.http;

import com.ruoyi.gateway.client.ClientComponent;
import com.ruoyi.gateway.client.IotClient;

import java.util.function.Consumer;

/**
 * http客户端
 * @see okhttp3.OkHttpClient
 */
public class HttpClient implements IotClient<Object> {

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void init(Object arg) { }

    @Override
    public Object connect(Consumer<?> consumer, long timeout) {
        return null;
    }

    @Override
    public Object disconnect(boolean remove) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientComponent getClientComponent() {
        return null;
    }
}
