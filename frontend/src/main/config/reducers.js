import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import { reducer as toastrReducer } from 'react-redux-toastr';
import { sessionReducer } from 'redux-react-session';

const rootReducer = combineReducers({
    form: formReducer,
    toastr: toastrReducer,
    session: sessionReducer
});

export default rootReducer;