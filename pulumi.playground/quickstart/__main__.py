"""An AWS Python Pulumi program"""

import pulumi
from pulumi_aws import s3

# Create an AWS resource (S3 Bucket)
bucket = s3.Bucket('my-bucket', 
                   website={ 
                     'indexDocument': 'index.html' 
                   })

ownership_controls = s3.BucketOwnershipControls('ownership-controls',
                                                bucket=bucket.id,
                                                rule={
                                                  'objectOwnership': 'ObjectWriter'
                                                })

public_access_block = s3.BucketPublicAccessBlock('public-access-block',
                                                bucket=bucket.id,
                                                block_public_acls=False)

# Export the name of the bucket for the website
# Create an S3 bucket object
bucket_object = s3.BucketObject(
    'index.html',
    bucket=bucket.id,
    source=pulumi.FileAsset('index.html'),
    content_type='text/html',
    acl='public-read',
    opts=pulumi.ResourceOptions(depends_on=[ownership_controls, public_access_block]))


pulumi.export('bucket_name', bucket.id)
pulumi.export('bucket_endpoint', pulumi.Output.concat('http://', bucket.website_endpoint))
