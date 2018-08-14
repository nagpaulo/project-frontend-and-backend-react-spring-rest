import React from 'react';
import Grid from '../template/grid';

export default props => (
    <Grid cols={props.cols}>
        <div className='form-group'>
            {props.label ? <label htmlFor={props.name}>{props.label}</label> : ''}
            <input {...props.input} className='form'
                placeholder={props.placeholder} readOnly={props.readOnly}
                type={props.type} />
        </div>
    </Grid>    
)