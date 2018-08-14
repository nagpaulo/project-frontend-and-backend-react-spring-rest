import React from "react";
import ReactDOM from "react-dom";
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';
import { sessionService } from 'redux-react-session';

import promise from 'redux-promise';
import multi from 'redux-multi';
import thunk from 'redux-thunk';

import App from "./main/app";
import reducers from './main/config/reducers';

import Messages from './common/msg/messages';

const devTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();
const store = applyMiddleware(thunk,multi,promise)(createStore)(reducers,devTools);

const validateSession = (session) => { return true; }
const options = { refreshOnCheckAuth: true, redirectPath: '/home', driver: 'COOKIES', validateSession };
   
sessionService.initSessionService(store, options)
    .then(() => console.log('Redux React Session is ready and a session was refreshed from your storage'))
    .catch(() => console.log('Redux React Session is ready and there is no session in your storage'));

ReactDOM.render(
    <Provider store={store}>
        <div className="container-fluid">    
            <App />
            <Messages />
        </div>
    </Provider>,
    document.getElementById("app")
);