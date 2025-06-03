# Base image for building Angular app
FROM node:18-alpine as build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build -- --configuration production

# Stage 2: Use Nginx to serve the app
FROM nginx:alpine
COPY --from=build /app/dist/order-processing-frontend/browser /usr/share/nginx/html
EXPOSE 80
