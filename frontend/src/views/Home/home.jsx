import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import * as sessionActions from '../Login/loginAction';

class Home extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const { actions, user, authenticated } = this.props;
        return (
            <div>
                <h1>Teste</h1>
                <h5>{authenticated ? 'You are authenticated :)' : 'Error'}</h5>
                <button onClick={actions.logout}>LOGOUT</button>

            </div>
        );
    }
}

const { object, bool } = PropTypes;

Home.propTypes = {
    actions: object.isRequired,
    user: object.isRequired,
    authenticated: bool.isRequired
};

const mapStateToProps = (state) => ({
    user: state.session.user,
    authenticated: state.session.authenticated
});

const mapDispatchToProps = (dispatch) => {
    return {
        actions: bindActionCreators(sessionActions, dispatch)
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Home);
