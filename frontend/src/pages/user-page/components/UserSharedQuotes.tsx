import React, {useContext, useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {instance} from "../../../api/instance";
import {AuthContext} from "../../../App";
import UserQuote, {QuoteData} from "./UserQuote";

const sampleQuoets: Array<QuoteData> = []

function UserSharedQuotes() {
    const [quotes, setQuotes] = useState(sampleQuoets);
    const { subject } = useParams();
    const authContext = useContext(AuthContext);


    useEffect(() => {
        // get the quotes
        instance(authContext.token).get("/quote/get?userEmail=" + subject)
            .then(res => {
                const newQuoteList:Array<QuoteData> = [];
                res.data.forEach((e: { id: any; quote: any; userEmail: any; bookAuthor: any; book: any; }) => {
                    newQuoteList.push(
                        {
                            id: e.id,
                            quoteBody: e.quote,
                            quoteUserEmail: e.userEmail,
                            quoteBookAuthor: e.bookAuthor,
                            quoteBook: e.book
                        }
                    )
                })
                setQuotes(newQuoteList);
            })
    }, [])
    return (
        <ul className={"quotes"}>
            {
                quotes.map((e,i) =>
                    <UserQuote
                        key={i}
                        id={e.id}
                        quoteBody={e.quoteBody}
                        quoteBook={e.quoteBook}
                        quoteBookAuthor={e.quoteBookAuthor}
                        quoteUserEmail={e.quoteUserEmail}/>)
            }
        </ul>
    );
}

export default UserSharedQuotes;