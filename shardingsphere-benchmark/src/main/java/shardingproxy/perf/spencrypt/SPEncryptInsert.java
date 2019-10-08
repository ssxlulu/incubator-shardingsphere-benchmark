/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shardingproxy.perf.spencrypt;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import perfstmt.ShardingPerfStmt;
import service.util.config.SPDataSourceUtil;

import java.sql.SQLException;

/**
 * encrypt insert for sharding proxy.
 * @author nancyzrh
 */
public class SPEncryptInsert extends AbstractJavaSamplerClient {
    private static final String INSERT_STMT = ShardingPerfStmt.INSERT_STMT.getValue();
    
    static {
        SPDataSourceUtil.createDataSource("###", "sharding_db", "###", 3307, "###");
    }
    
    /**
     * get default params.
     * @return null
     */
    @Override
    public Arguments getDefaultParameters() {
        return null;
    }
    
    /**
     * setup.
     * @param context context
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
    }
    
    /**
     * run test.
     * @param context context
     * @return sample res
     */
    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult results = new SampleResult();
        results.setSampleLabel("SPEncryptInsert");
        results.sampleStart();
        try {
            SPDataSourceUtil.insertIou(INSERT_STMT, "sharding_db");
        } catch (SQLException ex) {
            results.setSuccessful(false);
            return results;
        } finally {
            results.sampleEnd();
        }
        results.setSuccessful(true);
        return results;
    }
}
