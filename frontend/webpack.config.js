const path = require('path');
const HtmlWebPackPlugin = require("html-webpack-plugin");
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');


const htmlWebpackPlugin = new HtmlWebPackPlugin({
  template: "./src/index.html",
  filename: "./index.html",
  title: 'Educacenso - Sistema de Validação e Processamento de Dados Escolares',
  favicon: "src/assets/img/favicon.ico"
});

const providePlugin = new webpack.ProvidePlugin({
    '$': "jquery",
    'jQuery': "jquery",
    'Popper': 'popper.js'
});

const extractTextPlugin = new ExtractTextPlugin('app.css');
var mode = process.env.NODE_ENV || 'development';
module.exports = {
    entry: "./src/index.jsx",
    output: {
        path: path.resolve('dist'),
        filename: 'bundle.js',
        publicPath: '/'
    },
    devtool: (mode === 'development') ? 'inline-source-map' : false,
    mode: mode,
    devServer: {
        port: 8000,
        contentBase: path.resolve('dist'),
        historyApiFallback: true
    },
    resolve: {
        extensions: ['.js', '.jsx'],
        alias: {
            modules: path.resolve(__dirname,'/node_modules')
        }
    },
    performance: {
        maxEntrypointSize: 800000,
        maxAssetSize: 400000
    },
    module: {
        rules: [
            {
                test: /.js[x]?$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader"
                }
            },{
                test: /\.(css)$/,
                use: ExtractTextPlugin.extract({
                    fallback: "style-loader",
                    use: "css-loader"
                })
            },{
                test: /\.(png|jpg|gif|svg|eot|ttf|woff|woff2)$/,
                loader: 'file-loader',
                options: {
                    limit: 10000
                }
            },{
                test: /font-awesome\.config\.js/,
                use: [
                  { loader: 'style-loader' },
                  { loader: 'font-awesome-loader' }
                ]
                },
        ]
  },
  plugins: [htmlWebpackPlugin,extractTextPlugin,providePlugin]
};