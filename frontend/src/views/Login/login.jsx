import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import LoginForm from './loginForm';
import { authentication, recuperarSenha, logout } from './loginAction';

import logo from '../../assets/img/logo_gov_white.png';

class Login extends Component {
    constructor(props){
        super(props);
    }

    render(){
        const { actions } = this.props;
        return (
            <div className="loginColumns animated fadeInDown">
                 <div className="row">
                    {/*<div className="col-md-6">
                        <h2 className="font-bold">Welcome to IN+</h2>
                        <p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.</p>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                        <p>When an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
                        <p><small>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</small></p>
                    </div>*/}
                    <div className="col-md-7 col-md-offset-2">
                        <LoginForm onSubmit={ actions.authentication } onSubmitModal={ recuperarSenha }/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-7 col-md-offset-2">
                        <hr/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-7 col-md-offset-2 rodape">
                        <div className="col-md-6">
                             Pronatec - Sistema Financeiro
                        </div>
                        <div className="col-md-6 text-right">
                        <small>© 2018</small>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

const { object } = PropTypes;

Login.propTypes = {
    actions: object.isRequired
};

const mapDispatchToProps = dispatch => { 
    return { actions: bindActionCreators({ authentication, recuperarSenha, logout },dispatch) };
}
export default connect(null, mapDispatchToProps)(Login);