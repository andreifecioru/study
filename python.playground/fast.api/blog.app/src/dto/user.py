from pydantic import BaseModel, ConfigDict, EmailStr, Field


class UserBaseSchema(BaseModel):
    username: str = Field(min_length=1, max_length=50)
    first_name: str = Field(min_length=1, max_length=30)
    last_name: str = Field(min_length=1, max_length=30)
    email: EmailStr = Field(max_length=200)


class UserCreateSchema(UserBaseSchema):
    pass


class UserResponseSchema(UserBaseSchema):
    model_config = ConfigDict(from_attributes=True)

    id: int
    full_name: str
    image_file: str | None
    image_path: str


class UserPatchSchema(BaseModel):
    username: str | None = Field(min_length=1, max_length=50, default=None)
    first_name: str | None = Field(min_length=1, max_length=30, default=None)
    last_name: str | None = Field(min_length=1, max_length=30, default=None)
    email: EmailStr | None = Field(max_length=200, default=None)
