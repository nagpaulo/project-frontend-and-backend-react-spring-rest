const SERVER_URL_PROD = "https://wseducacenso.seduc.ce.gov.br/";
const SERVER_URL_DESV = "http://localhost:8080/";

const CONFIG = {};
CONFIG.USER_GLOBAL = "academico_web";
CONFIG.PASS_GLOBAL = "YFXS7X3ZPWYXAXEB5X2558PX";

CONFIG.URL_ACCESS_TOKEN = "?access_token=";
CONFIG.URL_REFRESH_TOKEN = "?refresh_token=";

CONFIG.SERVER_URL = (location.hostname.indexOf("localhost") != -1 ? SERVER_URL_DESV : SERVER_URL_PROD);
CONFIG.DEVELOPMENT = (location.hostname.indexOf("localhost") != -1 ? 'DESENVOLVIMENTO' : '');



CONFIG.URL_TOKEN_OAUTH = `${CONFIG.SERVER_URL}auth`;

export const APPLICATION = CONFIG;