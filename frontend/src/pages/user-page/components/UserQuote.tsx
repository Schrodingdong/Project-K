import React, {useContext, useEffect, useState} from 'react';

export interface QuoteData {
    id: string,
    quoteBody: string,
    quoteUserEmail: string,
    quoteBook: string,
    quoteBookAuthor: string
}


function UserQuote(prop: QuoteData) {
    return (
        <li className={'quote'}>
            <div className={'quote-body'}>
                <p>{prop.quoteBody}</p>
            </div>
            <div className={'quote-footer'}>
                <p>{prop.quoteBook} - {prop.quoteBookAuthor}</p>
            </div>

        </li>
    );
}

export default UserQuote;