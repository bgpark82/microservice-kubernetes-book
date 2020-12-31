#!/bin/bash

echo "> find microsesrvice..."
PRODUCT_COMPOSITE_PID=$(pgrep -f product-composite-service)
RECOMMENDATION_PID=$(pgrep -f recommendation-service)
RPRODUCT_PID=$(pgrep -f product-service)
REVIEW_PID=$(pgrep -f review-service)

echo product-composite pid: "$PRODUCT_COMPOSITE_PID"
echo recommendation pid: "$RECOMMENDATION_PID"
echo product pid: "$RPRODUCT_PID"
echo review pid: "$REVIEW_PID"

echo "> check pid"
function kill_pid() {
  if [ -z "$1" ]; then
    echo "$2 is not running"
  else
    echo "kill $2: $1"
    kill -9 "$1"
    sleep 5
  fi
}

kill_pid "$PRODUCT_COMPOSITE_PID" "product-composite"
kill_pid "$RECOMMENDATION_PID" "recommendation"
kill_pid "$RPRODUCT_PID" "product"
kill_pid "$REVIEW_PID" "review"


