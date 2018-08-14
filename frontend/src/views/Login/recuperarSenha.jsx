import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { reduxForm, Field } from 'redux-form';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import labelAndInput from '../../common/form/labelAndInput';
import { createTextMask } from 'redux-form-input-masks';

class RecuperarSenha extends Component {
    constructor(props){
        super(props);
    }

    render(){
        const { show, handleClose, handleSubmit ,submitting } = this.props;
        const cpfMask = createTextMask({
            pattern: '999.999.999-99'
        });
        return(
            <Modal show={show} onHide={handleClose}>
                <form method="post" onSubmit={handleSubmit}>
                <Modal.Header closeButton>
                    <Modal.Title>Recuperar Senha</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="row">
                        <div className="col-xs-12 col-sm-12 col-md-12">
                            <div className="alert alert-info" role="alert">
                                <b>Atenção: </b> O envio do e-mail com sua nova senha, poderá demorar alguns minutos para chegar. 
                                Caso não chegue na "Caixa de Entrada", verifique o "SPAM" do seu e-mail.
                            </div>
                        </div>
                        <div className="col-xs-12 col-sm-12 col-md-12">
                            <label htmlFor="">Informe o CPF para que seja feito o envio de uma nova senha.<br /></label>
                        </div>
                        <div className="col-xs-12 col-sm-12 col-md-12">
                            <Field name="cpf" className="form" placeholder="CPF" component="input" type="text" {...cpfMask}/>
                        </div>    
                    </div>   
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={handleClose} className="btn btn-default"><FontAwesomeIcon icon="times-circle" />  Fechar</Button>
                    <Button type="submit" className="btn" disabled={submitting}><FontAwesomeIcon icon="share-square" /> Enviar</Button>
                </Modal.Footer>
                </form>
            </Modal>
        )
    }
}

export default reduxForm({ form: 'recuperarSenha' })(RecuperarSenha);