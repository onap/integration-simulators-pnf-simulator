import json
import logging as sys_logging

from flask import Flask, request, logging
app = Flask(__name__)

sys_logging.basicConfig(level=sys_logging.DEBUG)
logger = logging.create_logger(app)
events = []


@app.route("/events/unauthenticated.VES_NOTIFICATION_OUTPUT", methods=['POST'])
def event_ves_notification_output():
    return handle_new_event(request)


@app.route("/events/unauthenticated.SEC_FAULT_OUTPUT", methods=['POST'])
def event_sec_fault_output():
    return handle_new_event(request)


def handle_new_event(http_request):
    receive_events = decode_request_data(http_request.data)
    for event in receive_events:
        events.append(json.loads(event))
    return {}, 200


@app.route("/events", methods=['GET'])
def get_events():
    return json.dumps(events), 200


def decode_request_data(request_data):
    data = request_data.decode("utf-8")
    receive_events = data.split("\n")
    receive_events = receive_events[:-1]
    logger.info("received events: " + str(receive_events))
    correct_events = []
    for event in receive_events:
        logger.info("received event: " + str(event))
        correct_events.append(get_correct_json(event))

    return correct_events


def get_correct_json(incorrect_json):
    json_start_position = incorrect_json.find("{")
    correct_json = incorrect_json[json_start_position:]
    correct_json = correct_json.replace("\r", "").replace("\t", "").replace(" ", "")
    return correct_json


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=3904)


