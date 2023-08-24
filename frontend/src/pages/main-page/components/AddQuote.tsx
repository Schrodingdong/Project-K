import React, {useContext, useState} from 'react';
import {getJwt, instance} from "../../../api/instance";
import {AuthContext, utilProps} from "../../../App";
import {useNavigate} from "react-router-dom";

interface AddQuoteProps extends utilProps{
    setAddQuote: (b: boolean) => void
}

function AddQuote(props: AddQuoteProps) {
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);
    const [quote, setQuote] = useState('');
    const [book, setBook] = useState('');
    const [author, setAuthor] = useState('');
    const saveQuote = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (quote.length === 0 || book.length === 0|| author.length === 0){
            props.addAlert('Incomplete information');
            return;
        }

        instance(authContext.token).post('/quote/save', {
            "quote": quote,
            "book": book,
            "bookAuthor": author
        }).then(res => {
            if (res.status === 200) {
                props.setAddQuote(false);
            } else {
                props.addAlert('Error saving the quote')
            }
            navigate("/home");
        }).catch(err => {
            console.error('error saving the quote : ' + err);
        })

    }
    return (
        <div className={'popup'}>
            <div className={'overlay'} onClick={e=> {props.setAddQuote(false)}}></div>
            <div className={'popup-content quote-add-container'}>
                <h2>Add a new Quote</h2>
                <form onSubmit={saveQuote}>
                    <label htmlFor={'quote'}>Quote</label>
                    <input type={'text'} id={'quote'} name={'Quote'} onChange={e => {setQuote(e.target.value)}}/>
                    <label htmlFor={'book'}>Book</label>
                    <input type={'text'} id={'book'} name={'Book'} onChange={e => {setBook(e.target.value)}}/>
                    <label htmlFor={'author'}>Book Author</label>
                    <input type={'text'} id={'author'} name={'Book Author'} onChange={e => {setAuthor(e.target.value)}}/>
                    <input type={'submit'} className={'button-full'} value={'+  Add quote'}/>
                </form>
            </div>
        </div>
    );
}

export default AddQuote;