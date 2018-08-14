import React, { Component } from 'react';
import { reduxForm, Field } from 'redux-form';
import RecuperarSenha from './recuperarSenha';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    componentWillMount() {
        this.setState({ show: false, actions: [] })
    }

    handleClose() {
        this.setState({ show: false });
    }
    
    handleShow() {
        this.setState({ show: true });
    }

    render() {
        const { handleSubmit, submitting, onSubmitModal } = this.props;
        return (
            <div className="ibox-content">
                <form method="post" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <Field name="usuario" className="form-control" placeholder="Usuário" component="input" type="text" />
                    </div>
                    <div className="form-group">
                        <Field name="senha" className="form-control" placeholder="Senha" component="input" type="password" />
                    </div>
                    <button type="submit" className="btn btn-primary block full-width m-b" disabled={submitting}>
                        <FontAwesomeIcon icon="share-square" /> Acessar
                    </button>

                    <a href="#" onClick={this.handleShow}>
                        <small>Esqueçeu a senha?</small>
                    </a>
                    <RecuperarSenha handleClose={this.handleClose} show={this.state.show} onSubmit={onSubmitModal}/>
                    <p className="text-muted text-center">
                        <small>Não sou cadastrado</small>
                    </p>
                    <a className="btn btn-sm btn-white btn-block" href="register.html">Cadastrar</a>
                </form>
                <p className="m-t">
                    <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small>
                </p>
            </div>
        )
    }
}

export default reduxForm({ form: 'loginForm' })(LoginForm);