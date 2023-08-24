import React, {useContext, useState} from 'react';
import './SharedQuotes.css'
import Quote, {QuoteData} from "./Quote";
import {AuthContext, utilProps} from "../../../App";
import AddQuote from "./AddQuote";

interface SharedQuotesProps extends utilProps{
  quotes: Array<QuoteData>;
}

function SharedQuotes(props: SharedQuotesProps) {
    const [addQuote, setAddQuote] = useState(false);

    return (
        <div id='shared-quotes'>
            <div className='shared-quotes-header'>
                <h1>Shared Quotes</h1>
                <button className={'button-full'} onClick={e => {setAddQuote(true)}}>+ Add a quote</button>
            </div>
            <div className={'separation-bar'}></div>
            <ul className={"quotes"}>
                {
                    props.quotes.map((e,i) =>
                        <Quote
                            key={i}
                            id={e.id}
                            quoteBody={e.quoteBody}
                            quoteBook={e.quoteBook}
                            quoteBookAuthor={e.quoteBookAuthor}
                            quoteUserEmail={e.quoteUserEmail}/>)
                }
            </ul>
            {
                addQuote?
                    <AddQuote setAddQuote={setAddQuote} addAlert={props.addAlert}/>
                    :
                    <></>
            }
        </div>
    );
}

export default SharedQuotes;