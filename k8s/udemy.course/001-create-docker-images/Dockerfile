# Start with a clean base image
FROM alpine

# Install the deps
RUN apk add --no-cache redis

# (Optional) Add a health-check
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 CMD [ "redis-cli", "ping" ]

# Define the start command
USER redis
WORKDIR /data
CMD ["redis-server"]