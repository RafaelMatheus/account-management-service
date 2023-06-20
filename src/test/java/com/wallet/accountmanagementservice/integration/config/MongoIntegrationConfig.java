package com.wallet.accountmanagementservice.integration.config;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class MongoIntegrationConfig {
    private static MongodExecutable mongodExecutable;

    public static void startMongo() throws IOException {
        var ip = "localhost";
        var port = 27017;

        var mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        var starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    public static void stopMong() throws IOException {
        mongodExecutable.start();
    }
}
