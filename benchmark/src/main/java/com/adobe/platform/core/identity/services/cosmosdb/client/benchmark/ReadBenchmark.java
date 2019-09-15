package com.adobe.platform.core.identity.services.cosmosdb.client.benchmark;

import com.adobe.platform.core.identity.services.cosmosdb.client.CosmosDbConfig;
import com.adobe.platform.core.identity.services.cosmosdb.util.CosmosDbException;
import com.adobe.platform.core.identity.services.datagenerator.DataGenConfig;
import com.adobe.platform.core.identity.services.datagenerator.impl.TwoTableDataGen;
import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@State(Scope.Benchmark)
public class ReadBenchmark extends AbstractBenchmark{
    private static final Logger logger = LoggerFactory.getLogger(ReadBenchmark.class.getSimpleName());

    private TwoTableDataGen twoTableDataGen;

    @Setup(Level.Trial)
    public void setup(){
        DataGenConfig datagenConfig = new DataGenConfig();
        CosmosDbConfig cosmosConfig = new CosmosDbConfig();
        commonSetup(cosmosConfig);

        twoTableDataGen = new TwoTableDataGen(client, datagenConfig, cosmosConfig);
        twoTableDataGen.verifyCollections();
        twoTableDataGen.fetchQueryKeys();
    }

    @Benchmark
    public String lookupRoutingSingle(){
        return performWorkload(twoTableDataGen.lookupRoutingSingle());
    }

    @Benchmark
    public List<String> lookupRoutingBatch() {
        return performWorkload(twoTableDataGen.lookupRoutingBatch());
    }

    @Benchmark
    public String lookupTwoTableSingle() {
        return performWorkload(twoTableDataGen.lookupTwoTableSingle());
    }

    @Benchmark
    public List<String> lookupTwoTableBatch() {
        return performWorkload(twoTableDataGen.lookupTwoTableBatch());
    }

    @TearDown
    public void tearDown(){
        commonTearDown();
    }

    // FOR DEBUGGING ONLY
    public static void main(String args[]) throws CosmosDbException {
        String challengeResponse = "Today's password is swordfish. I understand instantiating Blackholes directly " +
                                    "is dangerous.";
        int iterations = 10000;

        ReadBenchmark bench = new ReadBenchmark();
        bench.setup();

        try {
            for (int i = 0; i < iterations; i++) {
                long startTime = System.currentTimeMillis();
                //bench.twoTableLookup();
                bench.lookupTwoTableBatch();
                logger.info("Latency = {} ms", (System.currentTimeMillis() - startTime));
            }
        }
        catch(Throwable th) {
            throw th;
        }
        finally
        {
            bench.tearDown();
        }
    }
}
