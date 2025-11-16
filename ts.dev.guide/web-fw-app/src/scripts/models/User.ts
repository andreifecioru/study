import { CONSTS } from '../constants';
import { BaseModel, BaseModels, BaseProps } from './Model';

interface UserProps extends BaseProps {
  id?: number, // the ? marks the property as optional (default value is undefined)
  name?: string, 
  age?: number;
}


class User extends BaseModel<UserProps> {
  static API_ENDPOINT = `${CONSTS.BACKEND_URL}/users`;

  static empty(): User {
    return new User({});
  }

  constructor(props: UserProps) {
    super(props, User.API_ENDPOINT);
  }
}

class Users extends BaseModels<UserProps, User> {
  static API_ENDPOINT = `${CONSTS.BACKEND_URL}/users`;

  constructor() {
    super(Users.API_ENDPOINT, User);
  }
}

export { User, Users, UserProps };