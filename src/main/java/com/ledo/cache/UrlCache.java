package com.ledo.cache;

import com.ledo.beans.UrlContent;

import java.util.List;

/**
 * 缓存最新的网页内容
 * @author qgl
 * @date 2018/12/17
 */
public class UrlCache {
    private static UrlCache instance = new UrlCache();
    private CacheUrlContent cacheUrlContent;

    public static UrlCache getInstance() {
        return instance;
    }

    public CacheUrlContent getCacheUrlContent() {
        return cacheUrlContent;
    }

    public void setCacheUrlContent(CacheUrlContent cacheUrlContent) {
        this.cacheUrlContent = cacheUrlContent;
    }

    /**
     * 缓存网页内容（新增时间戳字段）
     */
    public class CacheUrlContent {
        private long timestamp;
        private List<UrlContent> allUrlContents;

        public CacheUrlContent(long timestamp, List<UrlContent> allUrlContents) {
            this.timestamp = timestamp;
            this.allUrlContents = allUrlContents;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public List<UrlContent> getAllUrlContents() {
            return allUrlContents;
        }

        public void setAllUrlContents(List<UrlContent> allUrlContents) {
            this.allUrlContents = allUrlContents;
        }
    }
}
