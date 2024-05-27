# Use the official RabbitMQ image as base image
FROM rabbitmq:3-management

# Copy custom RabbitMQ configuration file
COPY rabbitmq.conf /etc/rabbitmq/rabbitmq.conf
# COPY definitions.json /etc/rabbitmq/definitions.json

# Set the file permissions for the custom configuration file
RUN chmod 644 /etc/rabbitmq/rabbitmq.conf

# Set the file owner for the custom configuration file
# Envirment
ENV RABBITMQ_DEFAULT_USER=admin \
    RABBITMQ_DEFAULT_PASS=admin \
    RABBITMQ_DEFAULT_VHOST=/ \
    # RABBITMQ_ERLANG_COOKIE=secret \
    RABBITMQ_CONFIG_FILE=/etc/rabbitmq/rabbitmq.conf 
    # RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS=
    # RABBITMQ_LOG_BASE=/var/log/rabbitmq \
    # RABBITMQ_MNESIA_BASE=/var/lib/rabbitmq/mnesia \
    # RABBITMQ_ENABLED_PLUGINS_FILE=/etc/rabbitmq/enabled_plugins


# Expose ports for AMQP and management UI
EXPOSE 5672 15672