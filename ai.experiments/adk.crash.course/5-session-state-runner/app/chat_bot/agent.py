from google.adk.agents import Agent

# create the root_agent
chat_bot_agent = Agent(
  name="chat_bot_agent",
  model="gemini-2.5-flash",
  description="ChatBot that answers generic questions",

  # NOTE: you can inject information from the session "state" by interpolating any of the available keys
  instruction="""
  You are a helpful assistant ready to answer questions about the user prefefences.

  Here is some information about the user:
  Name:
  {user_name}
  Preferences:
  {user_preferences}
  """,
)
