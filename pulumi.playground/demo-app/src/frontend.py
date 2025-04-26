import pulumi
import pulumi as pm
from pulumi_aws import cloudfront as cf, amplify

project_name = pm.get_project()
stack_name = pm.get_stack()


def create_cf_distribution(backend_data):
    origin = cf.DistributionOriginArgs(
        domain_name=backend_data['eb.environment.endpoint_url'],
        origin_id=f'alb-origin-{project_name}-{stack_name}',
        custom_origin_config=cf.DistributionOriginCustomOriginConfigArgs(
            http_port=80,
            https_port=443,
            origin_protocol_policy='http-only',
            origin_ssl_protocols=['TLSv1.2'],
        )
    )

    cache_behavior = cf.DistributionDefaultCacheBehaviorArgs(
        target_origin_id=origin.origin_id,
        viewer_protocol_policy='redirect-to-https',
        allowed_methods=["GET", "HEAD", "OPTIONS", "PUT", "POST", "PATCH", "DELETE"],
        cached_methods=['GET', 'HEAD', 'OPTIONS'],
        forwarded_values=cf.DistributionDefaultCacheBehaviorForwardedValuesArgs(
            query_string=False,
            cookies=cf.DistributionDefaultCacheBehaviorForwardedValuesCookiesArgs(
                forward="all",
            ),
            headers=["Authorization"],  # Forward Authorization header to origin
        ),

        # Attach the response headers policy for CORS and security headers
        response_headers_policy_id="67f7725c-6f97-4210-82d7-5512b31e9d03",
    )

    restrictions = cf.DistributionRestrictionsArgs(
        geo_restriction=cf.DistributionRestrictionsGeoRestrictionArgs(
            restriction_type='none',
        )
    )

    viewer_certificate = cf.DistributionViewerCertificateArgs(
        cloudfront_default_certificate=True,
    )

    return cf.Distribution(
        f'{project_name}-{stack_name}-cf',
        comment=f'CloudFront Distribution for {project_name} ({stack_name})',
        enabled=True,
        is_ipv6_enabled=False,
        origins=[origin],
        default_cache_behavior=cache_behavior,
        restrictions=restrictions,
        viewer_certificate=viewer_certificate,
        tags={
            'Name': f'{project_name}-{stack_name}-oai',
            'Project': project_name,
            'Environment': stack_name
        }
    )


def create_amplify_branch(global_data):
    return amplify.Branch(
        f"{project_name}-{stack_name}",
        app_id=global_data['applications.amplify.id'],
        branch_name=stack_name,
        tags={
            "Name": f"{project_name}-{stack_name}",
            "Project": project_name,
            "Environment": stack_name
        }
    )


def create_frontend(global_data, backend_data):
    cf_distribution = create_cf_distribution(backend_data)
    amplify_branch = create_amplify_branch(global_data)

    return {
        'cf.endpoint_url': cf_distribution.domain_name,
        'frontend.endpoint_url': pulumi.Output.all(
            amplify_branch.branch_name,
            global_data["applications.amplify.domain"]
        ).apply(lambda args: f'https://{args[0]}.{args[1]}'),
    }
