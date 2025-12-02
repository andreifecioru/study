import { Router } from "express";
import type { Request, Response } from "express";

// improve Request type defintion with better type checking
interface RequestWithBody extends Request {
  body: { [key: string]: string | undefined }
}

const router = Router();
router.get('/', (req: Request, res: Response) => {
  if (req.session?.loggedIn) {
    res.send(`
      <div>
        You are now logged in!
      </div>
      <div>
        <form action="/logout" method="POST">
          <button type="submit">Logout</button>
        </form>
      </div>
    `);
  } else {
    res.send(`
      <div>
        Please <a href="/login">login</a>
      </div>
    `);
  }
});

router.get('/login', (req: Request, res: Response) => {
  res.send(`
      <form method="POST">
        <div>
          <label>Email: </label>
          <input name="email" />
        </div>

        <div>
          <label>Password: </label>
          <input name="password" type="password" />
        </div>

        <button type="submit">Submit</button>
      </form>
  `);
});

router.post('/login', (req: RequestWithBody, res: Response) => {
  const {email, password } = req.body;

  if (email && password && email === 'hi@hi.com' && password === '1121') {
    // update the session
    req.session = { loggedIn: true };

    res.redirect('/');
  } else {
    res.send(`
      <div>AuthN failed. Try <a href="/login">again</a> </div>`
    );
  }
});

router.post('/logout', (req: Request, res: Response) => {
  req.session = { loggedIn: false };
  res.redirect('/');
});

export { router };
