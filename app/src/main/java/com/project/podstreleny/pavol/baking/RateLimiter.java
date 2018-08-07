package com.project.podstreleny.pavol.baking;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

public class RateLimiter<Key> {

    private TimeUnit timeUnit;
    private Long timeOuts;

    private final ArrayMap<Key, Long> timestamps = new ArrayMap<>();
    private final Long timeout;

    public RateLimiter(long timeOut, TimeUnit timeUnit){
        this.timeOuts = timeOut;
        this.timeUnit = timeUnit;
        timeout = timeUnit.toMillis(timeOuts);
    }


    public synchronized boolean shouldFetch(Key key){
        final Long lastFetch = timestamps.get(key);
        final Long now = now();
        if(lastFetch == null){
            timestamps.put(key,now);
            return true;
        }
        if(now - lastFetch > timeout){
            timestamps.put(key,now);
            return true;
        }
        return false;

    }

    private long now(){
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(Key key){
        timestamps.remove(key);
    }

}
