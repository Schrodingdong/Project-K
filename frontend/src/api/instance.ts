import axios from "axios";

export const instance = axios.create({
    baseURL: "http://localhost:8888",
    timeout: 1000,

  }
);

function parseCookies(): { [key: string]: string } {
  const cookiesString = document.cookie;
  const cookies: string[] = cookiesString.split(';');
  const result: { [key: string]: string } = {};

  cookies.forEach((cookie) => {
    const [key, value] = cookie.trim().split('=');
    result[key] = decodeURIComponent(value);
  });

  return result;
}



export function getJwt(): string | null {
  const cookies = parseCookies();
  return ('jwt' in cookies)?cookies['jwt'] : null; 
}

export function getSubject(): string | null {
  const cookies = parseCookies();
  return ('subject' in cookies)?cookies['subject'] : null; 
}