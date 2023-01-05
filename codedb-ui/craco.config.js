// const { BundleAnalyzerPlugin } = require("webpack-bundle-analyzer");
// const WebpackBar = require("webpackbar");

const CracoAntDesignPlugin = require("craco-antd");
const path = require("path");

// Don't open the browser during development
process.env.BROWSER = "none";

module.exports = {
  webpack: {
    alias: {
      'antd/lib/theme': false,
    },
    plugins: [
      // new WebpackBar({ profile: true }),
      // ...(process.env.NODE_ENV === "development"
      //   ? [new BundleAnalyzerPlugin({ openAnalyzer: false })]
      //   : []),
    ],
  },
  plugins: [
    {
      plugin: CracoAntDesignPlugin,
      options: {
        customizeThemeLessPath: path.join(
          __dirname,
          "src/style/antd/customTheme.less"
        ),
        babelPluginImportOptions: {
          libraryName: 'antd',
          libraryDirectory: 'es',
          style: true,
        },
      },
    },
  ],
};
