FROM php:7.4-apache

# Copy your custom configuration file into the container
COPY conf/custom.conf /etc/apache2/conf-available/custom.conf

# Enable the custom configuration
RUN a2enconf custom
