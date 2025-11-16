import { CONSTS } from '../constants';
import { BaseModel, ModelState, BaseProps } from './Model';

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

export { User, UserProps };