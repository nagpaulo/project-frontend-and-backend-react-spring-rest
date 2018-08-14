import React from 'react'
import { Router, Route, IndexRoute } from 'react-router';
import App from "../app";
import Login from '../../views/Login/login';
import Home from '../../views/Home/home';
import { sessionService } from 'redux-react-session';

const RouterConfig = () => (
    <Route path="/" component={App}>
        <IndexRoute onEnter={sessionService.checkAuth} component={Home} />
        <Route path="login" component={Login} />
    </Route>   
);

$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});

export default RouterConfig;