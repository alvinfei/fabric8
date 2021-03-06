/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.kubernetes.api;

/**
 */
public class KubernetesNames {
    protected static final char DIGIT_PREFIX = 'n';

    /**
     * Lets convert the string to btw a valid kubernetes resource name
     */
    public static String convertToKubernetesName(String text, boolean allowDots) {
        String lower = text.toLowerCase();
        StringBuilder builder = new StringBuilder();
        boolean started = false;
        char lastCh = ' ';
        for (int i = 0, last = lower.length() - 1; i <= last; i++) {
            char ch = lower.charAt(i);
            boolean digit = ch >= '0' && ch <= '9';
            // names cannot start with a digit so lets add a prefix
            if (digit && builder.length() == 0) {
                builder.append(DIGIT_PREFIX);
            }
            if (!(ch >= 'a' && ch <= 'z') && !digit) {
                if (ch == '/') {
                    ch = '.';
                } else if (ch != '.' && ch != '-') {
                    ch = '-';
                }
                if (!allowDots && ch == '.') {
                    ch = '-';
                }
                if (!started || lastCh == '-' || lastCh == '.' || i == last) {
                    continue;
                }
            }
            builder.append(ch);
            started = true;
            lastCh = ch;
        }
        return builder.toString();
    }
}
