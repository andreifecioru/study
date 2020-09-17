#!/usr/bin/env python
import click
import time
import pprint

from random import randint

import requests
from requests import HTTPError

PP = pprint.PrettyPrinter(indent=2)
TSDB_URL = "http://tsdb-box:4242/api/put"


def show_banner(name, count, min_val, max_val, interval, tags):
    click.echo("-----------------[ TSDB metric generator ]--------------------")
    click.echo(f"Metric name: {name}")
    click.echo(f"Data-point count: {count}")
    click.echo(f"Min. data-point value: {min_val}")
    click.echo(f"Max. data-point value: {max_val}")
    click.echo(f"Interval (sec): {interval}")
    click.echo("Tags:")
    for k, v in tags:
        click.echo(f"  - {k} -> {v}")
    click.echo("--------------------------------------------------------------")


def generate_payload(name, value, tags, interval):
    time.sleep(interval)
    payload = {
        "metric": name,
        "timestamp": int(time.time()),
        "value": value,
        "tags": dict(tags),
    }
    return payload


def send_payload(payload):
    click.echo(f"Sending data-point: {payload['metric']} -> {payload['value']}")
    try:
        response = requests.post(TSDB_URL, json=payload)
        response.raise_for_status()
    except HTTPError as err:
        click.echo(f"Failed to send data-point: {err}")


@click.command()
@click.option("--name", required=True, help="Name of the TSDB metric.")
@click.option("--count", type=int, default=10, help="Numbers of data points to be sent.")
@click.option("--min-val", type=int, default=0, help="Min. value for data points.")
@click.option("--max-val", type=int, default=10, help="Max. value for data points.")
@click.option("--interval", type=float, default=1.0, help="Time span (in sec) between data points.")
@click.option("--tag", type=(str, str), multiple=True, help="K/V pair to be attached as metric tag.")
def main(name, count, min_val, max_val, interval, tag):
    tags = tag

    show_banner(name, count, min_val, max_val, interval, tags)

    values = (randint(min_val, max_val) for num in range(count))
    payloads = (generate_payload(name, value, tags, interval) for value in values)

    for payload in payloads:
        send_payload(payload)


if __name__ == "__main__":
    main()
