// config-overrides.js
const webpack = require('webpack');

module.exports = function override(config, env) {
  // Добавляем полифиллы для Node.js модулей
  config.resolve.fallback = {
    ...config.resolve.fallback,
    "url": require.resolve("url/"),
    "util": require.resolve("util/"),
    // ИСПРАВЛЕНИЕ: Убедитесь, что эта строка выглядит именно так
    "net": false, 
    "stream": require.resolve("stream-browserify")
  };

  // Добавляем плагин для Buffer, который тоже часто требуется
  config.plugins = (config.plugins || []).concat([
    new webpack.ProvidePlugin({
      Buffer: ['buffer', 'Buffer'],
    }),
  ]);
  
  return config;
};
