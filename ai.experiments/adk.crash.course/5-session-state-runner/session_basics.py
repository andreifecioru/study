from google.adk.sessions import InMemorySessionService

# A session is a stateful message history (maintained on a per-user basis)
#  - events (the conversation history)
#  - state (a dict of k/v pairs)

# A runner wraps around a collection of agents and a set of per-user sessions

# Create a simple session and inspect its properties
session_service = InMemorySessionService()
session = session_service.create_session_sync(
  app_name="my_app",
  user_id="the_user",
  # state can be initialized
  state={
    "my_key": "my_value",
  },
)

print("-----------[ Session props ]------------")
print(f"ID: {session.id}")
print(f"App name: {session.app_name}")
print(f"User ID: {session.user_id}")
print(f"State: {session.state}")
print(f"Events: {session.events}")
print(f"Last update: {session.last_update_time}")
print("----------------------------------------")
