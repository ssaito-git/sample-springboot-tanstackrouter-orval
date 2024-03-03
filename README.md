# sample-springboot-tanstackrouter-orval

Spring Boot で構築した Web API から OpenAPI 仕様を作成し orval で TypeScript のクライアントコードを生成します。
生成したクライアントコードを TanStack Query/Router から利用するサンプルです。

## 実行

パッケージのインストール。

```shell
./gradlew :front:npmCleanInstall
```

クライアントコードの生成。

```shell
./gradlew :front:generateOpenApiClient
```

Web API の起動。

```shell
./gradlew :api:bootRun
```

開発サーバーの起動。

```shell
./gradlew :fron:npmRunDev
```

開発サーバーに接続する。
