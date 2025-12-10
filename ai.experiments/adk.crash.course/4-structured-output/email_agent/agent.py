from google.adk.agents import Agent
from pydantic import BaseModel, Field


class EmailContent(BaseModel):
  subject: str = Field(
    description="The subject of the email. Should be short and precise.",
  )

  body: str = Field(
    description="The main content of the email. Should be well-formatted, with proper greeting, paragraphs and a closing signature.",
  )


root_agent = Agent(
  name="email_agent",
  model="gemini-2.5-flash",
  description="An agent capable of genrating emails",
  instruction="""
    You are a helpful assistant that can write emails.
    Your task is to generate proffessional emails based on the user's request.

    GUIDELINES:
    - Create an appropriate subject line (concise and relevant)
    - Write a well-structured email body with:
      + proffessional greeting
      + clear and concise main content
      + appropriate closing remark
      + a signature with your name
    - Email tone should match the purpose (formal for bussiness, friendly for colleagues)
    - Keep it concise, but complete

    IMPORTANT: You response MUST be valid JSON following this structure:
    {
      "subject": "Subject line here",
      "body": "E-mail body here"
    }

    DO NOT include any additional explanation/info outside the JSON document.
    """,
  output_schema=EmailContent,
  output_key="email",
)
