/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kcache;

import io.kcache.exceptions.CacheInitializationException;
import io.kcache.utils.SASLClusterTestHarness;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KafkaCacheSASLTest extends SASLClusterTestHarness {
    @Test
    public void testInitialization() throws Exception {
        Cache<String, String> kafkaCache = CacheUtils.createAndInitSASLCacheInstance(bootstrapServers);
        kafkaCache.close();
    }

    @Test(expected = CacheInitializationException.class)
    public void testDoubleInitialization() throws Exception {
        try (Cache<String, String> kafkaCache = CacheUtils.createAndInitSASLCacheInstance(bootstrapServers)) {
            kafkaCache.init();
        }
    }

    @Test
    public void testSimplePut() throws Exception {
        try (Cache<String, String> kafkaCache = CacheUtils.createAndInitSASLCacheInstance(bootstrapServers)) {
            String key = "Kafka";
            String value = "Rocks";
            kafkaCache.put(key, value);
            String retrievedValue = kafkaCache.get(key);
            assertEquals("Retrieved value should match entered value", value, retrievedValue);
        }
    }
}
