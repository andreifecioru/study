from typing import Self, cast
from pydantic import (
  BaseModel,
  EmailStr,
  HttpUrl,
  SecretStr,
  ValidationError,
  field_validator,
  model_validator,
)


class User(BaseModel):
  username: str
  website: HttpUrl
  
  # this is the default 'after' validation
  # executed _after_ pydantic applied it's own validation rules
  @field_validator('username')
  @classmethod
  def validate_username(cls, value: str) -> str:
    if not value.replace('_', '').isalnum():
      # NOTE: pydantic catches ValueError and converts it to a ValidationError
      raise ValueError('User name must be alpha-numeric (underscored allowed)')
    return value.lower() # you can also do sanitization during validation
  
  # switch this validator to the _before_ mode
  # it will get executed before pydantic built-in validations
  @field_validator('website', mode='before')
  @classmethod
  def validate_website(cls, value: str) -> str:
    # NOTE: you can pass multiple values to startsWith()
    if value and not value.startswith(('http://', 'https://')):
      return f'https://{value}'
    return value
  
# model-validator example
class UserRegistration(BaseModel):
  email: EmailStr
  password: SecretStr
  passwordConfirmation: SecretStr
  
  @model_validator(mode='after')
  def password_match(self) -> Self: # receives the whole model _instance_
    if self.password != self.passwordConfirmation:
      raise ValueError('Passwords to not match')
    return self

def main():
  try:
    user = User(
      username="A_FECIORU",
      # NOTE: if this validation fails, the custom validator for username will not be executed at all
      website=cast(HttpUrl, 'example.com'),  # casting in action
    )
  except ValidationError as err:
    print(f'ERROR: {err}')
  else:
    print(f'User: {user}')
    
  try:
    registration = UserRegistration(
      email='john.doe@email.com',
      password=cast(SecretStr, '1121'),
      passwordConfirmation=cast(SecretStr, '1122')
    )
  except ValidationError as err:
    print(f'ERROR: {err}')
  else:
    print(f'Registration: {registration}')


if __name__ == '__main__':
  main()